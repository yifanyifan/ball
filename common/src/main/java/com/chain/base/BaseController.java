package com.chain.base;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.common.ResultEntity;
import com.chain.constant.Constant;
import com.chain.service.RedisUtils;
import com.chain.validator.groups.Add;
import com.chain.validator.groups.Update;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public abstract class BaseController<S> {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 获取Service
     */
    public <T> T getServiceByObjectType() {
        Class<S> entityType = null;
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            Type[] typeArgs = ((ParameterizedType) superClass).getActualTypeArguments();
            if (typeArgs.length > 0 && typeArgs[0] instanceof Class) {
                entityType = (Class<S>) typeArgs[0];
            }
        }

        String[] serviceBeanNames = applicationContext.getBeanNamesForType(ServiceImpl.class);
        return (T) applicationContext.getBean(StrUtil.lowerFirst(entityType.getSimpleName()) + "ServiceImpl");
    }

    @ResponseBody
    @PostMapping("/add")
    @PreAuthorize("#root.this.getRequiredAuthority('ENTITY_CREATE') or hasAnyAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "基础功能-新增", httpMethod = "POST", response = ResultEntity.class)
    public ResultEntity add(@Validated(Add.class) @RequestBody S entity) {
        try {
            ServiceImpl service = getServiceByObjectType();
            service.save(entity);
            return ResultEntity.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/edit")
    @PreAuthorize("#root.this.getRequiredAuthority('ENTITY_UPDATE') or hasAnyAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "基础功能-修改", httpMethod = "PUT", response = ResultEntity.class)
    public ResultEntity edit(@Validated(Update.class) @RequestBody S entity) {
        try {
            ServiceImpl service = getServiceByObjectType();
            service.updateById(entity);
            return ResultEntity.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("/id/{id}")
    @PreAuthorize("#root.this.getRequiredAuthority('ENTITY_DELETE') or hasAnyAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "基础功能-删除", httpMethod = "DELETE", response = ResultEntity.class)
    public ResultEntity deleteById(@PathVariable("id") Long id) {
        try {
            ServiceImpl service = getServiceByObjectType();
            service.removeById(id);
            return ResultEntity.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/id/{id}")
    @PreAuthorize("#root.this.getRequiredAuthority('ENTITY_READ') or hasAnyAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "基础功能-通过ID查询单条记录", notes = "基础功能-通过ID查询单条记录", httpMethod = "GET", response = ResultEntity.class)
    public ResultEntity queryById(@PathVariable("id") Long id) {
        try {
            ServiceImpl service = getServiceByObjectType();
            S entity = (S) service.getById(id);
            return ResultEntity.success(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/queryByParam")
    @PreAuthorize("#root.this.getRequiredAuthority('ENTITY_READ') or hasAnyAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "基础功能-条件查询", httpMethod = "POST", response = ResultEntity.class)
    public ResultEntity queryByParam(@RequestBody S entity) {
        try {
            QueryWrapper<S> wrapper = new QueryWrapper<>();
            if (ObjectUtil.isNotEmpty(entity)) {
                Class<?> clazz = entity.getClass();
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equals("serialVersionUID")) {
                        continue;
                    }
                    Object value = field.get(entity);
                    if (ObjectUtil.isNotEmpty(value)) {
                        wrapper.eq(StrUtil.toUnderlineCase(field.getName()), value);
                    }
                }
            }

            ServiceImpl service = getServiceByObjectType();
            List<S> list = service.list(wrapper);
            return ResultEntity.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/queryPageByParam")
    @PreAuthorize("#root.this.getRequiredAuthority('ENTITY_READ') or hasAnyAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "基础功能-条件查询分页", httpMethod = "POST", response = ResultEntity.class)
    public ResultEntity baseQueryPageByParam(Page<S> page, @RequestBody S entity) {
        try {
            QueryWrapper<S> wrapper = new QueryWrapper<>();
            if (ObjectUtil.isNotEmpty(entity)) {
                Class<?> clazz = entity.getClass();
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equals("serialVersionUID")) {
                        continue;
                    }
                    Object value = field.get(entity);
                    if (ObjectUtil.isNotEmpty(value)) {
                        wrapper.eq(StrUtil.toUnderlineCase(field.getName()), value);
                    }
                }
            }

            ServiceImpl service = getServiceByObjectType();
            Page<S> iPage = (Page<S>) service.page(page, wrapper);
            return ResultEntity.success(iPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 判断当前接口是否有permissionName权限
     */
    public boolean getRequiredAuthority(String permissions) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String entityName = this.getClass().getSimpleName().replace("Controller", "");

        Boolean hasRole = authentication.getAuthorities().stream().anyMatch(i -> {
            String value = (String) RedisUtils.hGet(Constant.REDIS_RESOURCE_ROLE + i.getAuthority(), entityName);
            if(StringUtils.isEmpty(value)){
                return false;
            }
            return Arrays.asList(value.split(",")).stream().anyMatch(k -> permissions.equals("ENTITY_" + k));
        });

        return hasRole;
    }
}
