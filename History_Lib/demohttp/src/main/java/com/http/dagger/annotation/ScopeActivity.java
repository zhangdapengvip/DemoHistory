package com.http.dagger.annotation;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ZeroAries on 2016/4/12.
 */
@Scope
@Retention(RUNTIME)
public @interface ScopeActivity {}