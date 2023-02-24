package com.mrjiang.ssmdemo.business.user.controller;

import com.mrjiang.ssmdemo.business.user.service.impl.UserServiceImpl;
import com.mrjiang.ssmdemo.core.dto.MyPager;
import com.mrjiang.ssmdemo.core.dto.MyResult;
import com.mrjiang.ssmdemo.mybatis.domain.user.UserDO;
import com.wf.captcha.GifCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * 模块名称:用户信息管理-控制器
 * 模块类型:C-控制器
 * 编码人:施银江
 * 创建时间:2023/2/23
 * 联系时间:15912181467
 */
@Controller
@RequestMapping("/User")
@Slf4j
public class UserController {

    //注入业务层实例
    @Resource(name = "userServiceImpl")
    private UserServiceImpl userService;
    private String upDateUse;
    private Integer pageNub = 1;


    /**
     * 跳转登录
     * @return
     */
    @RequestMapping(value = "/toLogin",method = RequestMethod.GET)
    public String toLogin(){
        System.out.println("————进行登录页面跳转——");
        return "Login";
    }


    /**
     * 跳转去主页
     * @return
     */
    @RequestMapping(value = "/toMain",method = RequestMethod.GET)
    public String toMain(){
        System.out.println("————进行登录页面跳转——");
        return "Main";
    }


    /**
     * 登录判断
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    @ResponseBody
    public MyResult doLogin(@RequestBody UserDO user,HttpSession session){
        log.debug("用户信息管理:获取用户信息通过学生id和PWD[param:id ="+user.getUserId()+" param:pwd = "+user.getUserPwd()+"]");

        System.out.println("——————进行登录验证模块————————");
        MyResult result = new MyResult();


        UserDO userDO = userService.doLogin(user);



            if (userDO.getState() == 0){
                userDO.setState(1);
                userService.doState(userDO);
                session.setAttribute("state",userDO.getState());
                session.setAttribute("userId",userDO.getUserId());
                if (userDO.getUserId().equals(user.getUserId())){

                    //验证码验证
                    Object reqCode = session.getAttribute("reqCode");
                    String reqCodeText = user.getReqCodeText();
                    if(reqCode==null){//验证码过期
                        result.setCode(199);
                        result.setMsg("验证码过期从新填写");
                    }
                    else{
                        String reqCodeStr = (String)reqCode;

                        System.out.println(reqCodeStr);
                        System.out.println(reqCodeText);

                        if(reqCodeStr.equalsIgnoreCase(reqCodeText)) {//验证码输入正确
                            result.setCode(200);
                            result.setMsg("验证码正确");

                        }else {
                            result.setCode(199);
                            result.setMsg("验证码错误！");
                        }
                    }
//            result.setMsg("欢迎!"+userDO.getUserName()+" 登录");
                    result.setData(userDO);
                    session.setAttribute("userName",userDO.getUserName());
                }else if (userDO.getUserId()==null){
                    result.setCode(500);
                    result.setMsg("用户不存在");
                } else {
                    result.setCode(500);
                    result.setMsg("用户名或密码错误");
                }
            }else {
                result.setCode(500);
                result.setMsg("不能重复登录！");
            }

        return result;
    }

    /**
     * 刷新验证码
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/toReqCode",method= RequestMethod.GET)
    @ResponseBody
    public MyResult reqCode(HttpSession session, Model model){
        System.out.println("—————进行验证码刷新———");
        GifCaptcha gifCaptcha = new GifCaptcha(100, 42, 4);
        String text = gifCaptcha.text();// 计算的文字结果值

        session.setMaxInactiveInterval(60);
        session.setAttribute("reqCode",text);
        //往前端返回一个图片
        String s = gifCaptcha.toBase64();
        MyResult result = new MyResult();
        result.setData(s);
        return result;
    }

    /**
     * 进行退出操作
     * @param outState
     * @return
     */
    @RequestMapping(value = "/doOut",method = RequestMethod.GET)
    @ResponseBody
    public MyResult doOut( int outState,HttpSession session){
        log.debug("前端获取数据 =["+outState+"]");

        System.out.println("*********进行退出业务模块*********");

//        System.out.println(outState);

        Object id = session.getAttribute("userId");

//        System.out.println(id);

        UserDO user = new UserDO();

        user.setUserId((String) id);
        user.setState(outState);

        int i = userService.doState(user);

        MyResult result = new MyResult();

        if (i==1){
            result.setCode(200);
            result.setMsg("已退出");
        }else {
            result.setCode(500);
            result.setMsg("发生未知的错误");
        }

        return result;

    }

    /**
     * 初始化表格
     * @param pager
     * @return
     */
    @RequestMapping(value = "/initTab",method = RequestMethod.GET)
    @ResponseBody
    public MyResult initTab(MyPager pager){
        log.debug("表格分页数据");

        System.out.println("——————表格初始化————————");
        MyResult result = new MyResult();
//        pager.setPage(page);
//        pager.setSize(size);
        System.out.println(pageNub);

        if (pageNub!=0){
            pager.setPage(pageNub);
        }

        //调用业务层
        MyPager<UserDO> userPage = userService.getList(pager);

        result.setData(userPage);
        result.setMsg("查询成功！");


        return result;

    }

    /**
     * 下一页操作
     * @param nub
     * @return
     */
    @RequestMapping(value = "/NextPage",method = RequestMethod.GET)
    @ResponseBody
    public MyResult NextPage( int nub){

        log.debug("前端获取数据 =["+nub+"]");

        System.out.println("*********进行下一页业务模块*********");

        System.out.println(nub);

        MyResult result = new MyResult();
        if (nub == 10){
            nub = 1;
            pageNub+=nub;
            result.setCode(200);
        }else {
            nub = 1;
            pageNub+=nub;
            result.setCode(201);
        }


        return result;

    }

    /**
     * 上一页操作
     * @param nub
     * @return
     */
    @RequestMapping(value = "/ReturnPage",method = RequestMethod.GET)
    @ResponseBody
    public MyResult ReturnPage( int nub){
        log.debug("前端获取数据 =["+nub+"]");

        System.out.println("*********进行上一页业务模块*********");

        System.out.println(nub);
        MyResult result = new MyResult();
        if (nub == -10){
            nub = -1;
            pageNub+=nub;
            result.setCode(200);
        }else {
            nub = -1;
            pageNub+=nub;
            result.setCode(201);
        }


        return result;

    }


    /**
     * 条件查询表格
     * @param pager
     * @param user
     * @return
     */
    @RequestMapping(value = "/queryTab",method = RequestMethod.POST)
    @ResponseBody
    public MyResult queryTab(MyPager pager,@RequestBody UserDO user){
        log.debug("条件查询表格数据["+user+"]");

        System.out.println("======条件表格加载=====");
        MyResult result = new MyResult();


        if (pageNub!=0){
            pager.setPage(pageNub);
        }

        //调用业务层
        MyPager<UserDO> queryUserPager = userService.queryList(pager,user);


        result.setData(queryUserPager);
        result.setMsg("查询成功！");


        return result;

    }

    /**
     * 新增操作
     * @param user
     * @return
     */
    @RequestMapping(value = "/doAdd",method = RequestMethod.POST)
    @ResponseBody
    public MyResult doAdd(@RequestBody UserDO user){
        log.debug("前端获取数据 =["+user+"]");

        System.out.println("——————进行新增模块————————");

        user.setState(0);

        MyResult result = new MyResult();
        int i = userService.doAdd(user);

        if (i==1){
            result.setMsg("新增成功！");
        }else {
            result.setCode(100);
            result.setMsg("新增失败！该用户已经存在！");
        }


        return result;
    }

    /**
     * 跳转修改传递参数
     * @param user
     * @return
     */
    @RequestMapping(value = "/toUp",method = RequestMethod.GET)
    @ResponseBody
    public MyResult toUp( UserDO user){
        log.debug("前端获取数据 =["+user+"]");

        System.out.println("——————进行编辑模块————————");

        MyResult result = new MyResult();

        UserDO userDO = userService.doLogin(user);

        upDateUse = userDO.getUserId();

       result.setData(userDO);

        return result;

    }


    /**
     * 修改操作
     * @param user
     * @return
     */
    @RequestMapping(value = "/doUp",method = RequestMethod.POST)
    @ResponseBody
    public MyResult doUp(@RequestBody UserDO user,HttpSession session){

//        Object code = session.getAttribute("Code");
//        System.out.println(code);

        user.setUserId(upDateUse);
        log.debug("前端获取编辑后的数据 =["+user+"]");

        System.out.println(user.getUserId());
        System.out.println("——————进行编辑模块————————");

        MyResult result = new MyResult();

        int i = userService.doUpdate(user);

        if (i==1){
            result.setMsg("修改成功！");
        }else {
            result.setCode(100);
            result.setMsg("修改失败！发生了未知的错误");
        }

        return result;
    }


    /**
     * 删除操作
     * @param user
     * @return
     */
    @RequestMapping(value = "/doDelete",method = RequestMethod.GET)
    @ResponseBody
    public MyResult doDelete(UserDO user){

        log.debug("前端获取要删除的数据 =["+user+"]");

        System.out.println("——————进行删除模块————————");

        MyResult result = new MyResult();

        int i = userService.doDelete(user.getUserId());

        if (i==1){
            result.setMsg("删除成功！");
        }else {
            result.setCode(100);
            result.setMsg("修改失败！发生了未知的错误");
        }


        return result;
    }

}
