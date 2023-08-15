package com.chain.service;

import com.chain.dto.UserDTO;
import com.chain.entity.User;
import com.chain.param.UserPageParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chain.common.PageEntity;
import java.util.List;

/**
 *  服务类
 *
 * @author 易樊
 * @since 2023-07-17
 */
public interface UserService extends IService<User> {

    /**
     * 保存
     *
     * @param user
     * @return
     * @throws Exception
     */
    boolean saveUser(User user) throws Exception;

    /**
     * 修改
     *
     * @param user
     * @return
     * @throws Exception
     */
    boolean updateUser(User user) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteUser(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param userPageParam
     * @return
     * @throws Exception
     */
    PageEntity<User> getUserPageList(UserPageParam userPageParam) throws Exception;

    /**
     * 获取列表对象
     *
     * @param userPageParam
     * @return
     * @throws Exception
     */
    List<User> getUserList(UserPageParam userPageParam) throws Exception;

    UserDTO loadUserByUsername(String username);
}
