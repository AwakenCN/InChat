package com.myself.nettychat.common.utils;

import com.myself.nettychat.vo.ResultVo;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:59 2018\10\7 0007
 */
public class ResultVOUtil {
    public static ResultVo success(Object object){
        ResultVo resultVO = new ResultVo();
        resultVO.setData(object);
        resultVO.setCode(200);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVo success(){
        return success(null);
    }

    public static ResultVo error(Integer code, String msg){
        ResultVo resultVO = new ResultVo();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
