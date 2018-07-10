package com.ns.warlock.service;

import com.ns.warlock.dto.SnDTO.Type;

public interface SnService {

    /**
     * 生成序列号
     * @param type
     * @return
     */
    String generate(Type type);

}
