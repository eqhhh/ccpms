package com.example.springbootccpms.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootccpms.common.Result;
import com.example.springbootccpms.entity.Permission;
import com.example.springbootccpms.entity.RolePermission;
import com.example.springbootccpms.entity.User;
import com.example.springbootccpms.entity.UserRole;
import com.example.springbootccpms.enums.PwdEnum;
import com.example.springbootccpms.enums.RoleEnum;
import com.example.springbootccpms.mapper.PermissionMapper;
import com.example.springbootccpms.mapper.RoleMapper;
import com.example.springbootccpms.mapper.UserMapper;
import com.example.springbootccpms.mapper.UserRoleMapper;
import com.example.springbootccpms.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
    @Resource
    UserMapper userMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    PermissionMapper permissionMapper;
    @Resource
    UserRoleMapper userRoleMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; //??????bcryct??????

    @PostMapping("/login")
    public Result<?> login(@RequestBody User userParam) {
        User userPwd = userMapper.selectByName(userParam.getUsername());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userParam.getUsername());

        try{
            queryWrapper.eq("password", userPwd.getPassword());
        }
        catch (NullPointerException e){
            return Result.error("-1", "??????????????????");
        }

        User res = userMapper.selectOne(queryWrapper);

        // ????????????????????????
        if (!bCryptPasswordEncoder.matches(userParam.getPassword(), userPwd.getPassword())) {
            return Result.error("-1", "????????????");
        }
        if (res == null) {
            return Result.error("-1", "????????????????????????");
        }
        HashSet<Permission> permissionSet = new HashSet<>();
        // 1. ???user_role???????????????id???????????????????????????
        Integer userId = res.getId();
        List<UserRole> userRoles = roleMapper.getUserRoleByUserId(userId);
        // ????????????id
        res.setRoles(userRoles.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList()));
        for (UserRole userRole : userRoles) {
            // 2.??????roleId???role_permission?????????????????????permissionId
            List<RolePermission> rolePermissions = permissionMapper.getRolePermissionByRoleId(userRole.getRoleId());
            for (RolePermission rolePermission : rolePermissions) {
                Integer permissionId = rolePermission.getPermissionId();
                // 3. ??????permissionId??????permission??????
                Permission permission = permissionMapper.selectById(permissionId);
                permissionSet.add(permission);
            }
        }
        // ???????????????id????????????
        LinkedHashSet<Permission> sortedSet = permissionSet.stream().sorted(Comparator.comparing(Permission::getId)).collect(Collectors.toCollection(LinkedHashSet::new));
        //?????????????????????????????????
        res.setPermissions(sortedSet);

        // ??????token
        String token = TokenUtils.genToken(res);
        res.setToken(token);
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (res != null) {
            return Result.error("-1", "???????????????");
        }
//        if (user.getPassword() == null) {
//            user.setPassword("123456");
//        }
        User userInfo = User.builder()
                .username(user.getUsername())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .nickName("??????" + IdWorker.getId())
                .build();

        userMapper.insert(userInfo);

        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);

        return Result.success();
    }

    @PostMapping
    public Result<?> save(@RequestBody User user) {
        if (user.getPassword() == null) {
            user.setPassword(bCryptPasswordEncoder.encode(PwdEnum.PASSWORD.getPassword()));
        }
        userMapper.insert(user);

        UserRole userRole = UserRole.builder()
                .userId(user.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);

        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }

    @PutMapping("/pass")
    public Result<?> pass(@RequestBody Map<String, Object> map) {
        User user = userMapper.selectById((Integer) map.get("userId"));
        if (user== null) {
            return Result.error("-1", "???????????????");
        }
        if (!bCryptPasswordEncoder.matches(map.get("password").toString(), user.getPassword())) {
            return Result.error("-1", "????????????");
        }
        map.put("newPass", (bCryptPasswordEncoder.encode(map.get("newPass").toString())));
        userMapper.updatePass(map);
        return Result.success();
    }

    // ??????????????????
    @PutMapping("/changeRole")
    public Result<?> changeRole(@RequestBody User user) {
        // ???????????????id?????????????????????????????????????????????
        roleMapper.deleteRoleByUserId(user.getId());
        // ????????? ??????????????????
        for (Integer roleId : user.getRoles()) {
            roleMapper.insertUserRole(user.getId(), roleId);
        }

        // ?????????????????????????????????id??????
        User currentUser = getUser();
        // ??????????????????????????????????????????????????????????????????id????????????????????????
        if (user.getId().equals(currentUser.getId())) {
            return Result.success(true);
        }
//        ???????????????????????????false???????????????????????????
        return Result.success(false);
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        userRoleMapper.deleteByUserRoleId(id);
        userMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(userMapper.selectById(id));
    }

    @GetMapping("/all")
    public Result<?> findAll() {
        return Result.success(userMapper.selectList(null));
    }

    /**
     * ????????????
     *
     * @return
     */
    @GetMapping("/countAddress")
    public Result<?> countAddress() {
//        User user = getUser(); // ???????????????????????????
        return Result.success(userMapper.countAddress());
    }

    @PutMapping("/recharge/{id}/{rechargeAmount}")
    public Result<?> recharge(@PathVariable("id") Integer id,
                              @PathVariable("rechargeAmount") BigDecimal rechargeAmount) {
        BigDecimal newAccount=userMapper.selectById(id).getAccount().add(rechargeAmount);
        userMapper.rechargeById(id,newAccount);
        return Result.success();
    }

    /**
     * ????????????????????????????????????????????????????????????
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {

        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().orderByAsc(User::getId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getUsername, search);
        }

        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        System.out.println(userPage);
        // ?????????????????????id??????
        for (User record : userPage.getRecords()) {
            List<UserRole> roles = roleMapper.getUserRoleByUserId(record.getId());
            List<Integer> roleIds = roles.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList());
            record.setRoles(roleIds);
        }
        return Result.success(userPage);
    }

    /**
     * Excel??????
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<User> all = userMapper.selectList(null);
        for (User user : all) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("ID", user.getId());
            row1.put("?????????", user.getUsername());
            row1.put("??????", user.getNickName());
            row1.put("??????", user.getSex());
            row1.put("??????", user.getAge());
            row1.put("??????", user.getAddress());
            row1.put("??????", user.getPhone());
            row1.put("??????", user.getEmail());
            row1.put("??????", user.getState());
            row1.put("??????", user.getRemark());
            list.add(row1);
        }

        // 2. ???excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("????????????", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    //???????????????????????????????????????
    /**
     * Excel??????
     * ??????????????????????????? Excel???????????????
     *
     * @param file Excel
     * @return
     * @throws IOException
     */
    @PostMapping("/import")
    public Result<?> upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<User> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setNickName(row.get(1).toString());
            user.setAge(Integer.valueOf(row.get(2).toString()));
            user.setSex(row.get(3).toString());
            user.setAddress(row.get(4).toString());
            saveList.add(user);
        }
        for (User user : saveList) {
            userMapper.insert(user);
        }
        return Result.success();
    }


}
