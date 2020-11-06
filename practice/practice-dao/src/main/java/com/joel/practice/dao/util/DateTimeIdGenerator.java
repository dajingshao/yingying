package com.joel.practice.dao.util;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DateTimeIdGenerator implements IdentifierGenerator {
    private Random random = new Random();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");

    @Override
    public Number nextId(Object entity) {
        synchronized (DateTimeIdGenerator.class) {
            int rand = random.nextInt(999);
            String id = String.format("%s%03d", dateFormat.format(new Date()), rand);
            return Long.parseLong(id);
        }
    }
}
