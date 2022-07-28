package com.medico.app.utils;

public class MaxLimit {
    int MAX = 10;
    int MIN = 1;
    int limit;
    public MaxLimit(int limit) {
        this.limit = limit;
        limit(limit);
    }
    public int limit(int a) {
        return (a > MAX) ? MAX : (a < MIN ? MIN : a);
    }
}
