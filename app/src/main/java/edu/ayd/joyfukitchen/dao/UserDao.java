package edu.ayd.joyfukitchen.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import edu.ayd.joyfukitchen.bean.User;
import edu.ayd.joyfukitchen.bean.WeightRecord;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserDao {

    private JoyFuDBHelper joyFuDBHelper;
    private Dao<WeightRecord, Integer> dao;

    //构造器
    public UserDao(Context context) {
        try {
            joyFuDBHelper = JoyFuDBHelper.getInstance(context);
            dao = joyFuDBHelper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
