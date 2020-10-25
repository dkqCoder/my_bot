package com.tty.user.dao;


import com.tty.user.dao.pojo.UserLevelGrowUp;
import com.tty.user.dto.UserLevelAndIntegralDTO;

/**
 * @author LiuJin
 */
public interface WebUserLevelGrowUpDao {

    public void saveUserLevelGrowUp(UserLevelGrowUp ent);

    public void updateUserLevelGrowUp(UserLevelGrowUp ent);

    public void deleteUserLevelGrowUp(UserLevelGrowUp ent);

    public void saveOrUpdateUserLevelGrowUp(UserLevelGrowUp ent);

    UserLevelAndIntegralDTO getUserLevelByUserId(Long userId);
}
