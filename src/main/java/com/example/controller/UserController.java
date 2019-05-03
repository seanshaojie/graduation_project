package com.example.controller;


import com.example.annotation.LogAnnotation;
import com.example.dao.UserDao;
import com.example.dto.UserDto;
import com.example.model.Page.PageTableHandler;
import com.example.model.Page.PageTableHandler.*;
import com.example.model.Page.PageTableRequest;
import com.example.model.Page.PageTableResponse;
import com.example.model.SysUser;
import com.example.service.UserService;
import com.example.utils.UserUtil;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Api(tags = "用户")
@RestController  /*@ResponseBody ＋ @Controller的结合*/
@RequestMapping("/users")
public class UserController {

@Autowired
private UserService userService;


@Autowired
private UserDao userDao;

private static final Logger log=Logger.getLogger("adminlogger") ;

/**@ClassName listUsers
 *@Description: 1、@RequestParam绑定单个请求参数值；
 *
 * 2、@PathVariable绑定URI模板变量值；
 *
 * 3、@CookieValue绑定Cookie数据值
 *
 * 4、@RequestHeader绑定请求头数据；
 *
 * 5、@ModelValue绑定参数到命令对象；
 *
 * 6、@SessionAttributes绑定命令对象到session；
 *
 * 7、@RequestBody绑定请求的内容区数据并能进行自动类型转换等。
 *
 * 8、@RequestPart绑定“multipart/data”数据，除了能绑定@RequestParam能做到的请求参数外，还能绑定上传的文件等。
 *@Data 2019/3/29
 *Author censhaojie
 */
    @LogAnnotation
    @ApiOperation(value = "保存用户")
    @PostMapping("/save")
    public SysUser saveUser(@RequestBody UserDto userDto){
        SysUser sysUser=userService.getUser(userDto.getUsername());
        if(sysUser!=null){
            throw new IllegalArgumentException(userDto.getUsername() + "已存在");
        }
        return userService.saveUser(userDto);
    }

    @ApiOperation(value = "根据用户id获取用户")
    @GetMapping("/{id}")
    @ApiImplicitParam(name = "id",value = "用户id",dataType = "String",required = true)
    public SysUser getUserById(@PathVariable String id){
        return userDao.getUserById(id);
    }

    @ApiOperation(value = "用户列表")
    @GetMapping("/list")
    public PageTableResponse listUsers(PageTableRequest request){
        return new PageTableHandler(new CountHandler() {
            @Override
            public int count(PageTableRequest request) {
                return userDao.count(request.getParams());
            }
        }, new ListHandler() {
            @Override
            public List<?> list(PageTableRequest request) {
                List<SysUser> list = userDao.list(request.getParams(), request.getOffset(), request.getLimit());
                return list;
            }
        }).handle(request);
    }

    @ApiOperation(value = "当前登录用户")
    @GetMapping("/current")
    public SysUser currentUser() {
        SysUser sysUser = UserUtil.getLoginUser();
        return UserUtil.getLoginUser();
    }

    @LogAnnotation
    @ApiOperation(value = "修改密码")
    @PutMapping("/{username}")
    @ApiImplicitParam(name = "username",value = "用户名",dataType = "String",required = true)
    public void changePassword(@PathVariable String username, String oldPassword, String newPassword ){
        userService.changePassword(username,oldPassword,newPassword);
    }

    @LogAnnotation
    @ApiOperation(value = "修改用户")
    @PutMapping("/update")
    public SysUser updateUser(@RequestBody UserDto userDto){
        return userService.update(userDto);
    }

    @LogAnnotation
    @ApiOperation(value = "修改头像")
    @PutMapping(params = "headImgUrl")
    public void updateHeadImgUrl(String headImgUrl){
        SysUser sysUser=UserUtil.getLoginUser();
        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(sysUser,userDto);
        userDto.setHeadImgUrl(headImgUrl);
        userService.update(userDto);
    }

}
