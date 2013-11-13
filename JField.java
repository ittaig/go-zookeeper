/* ==========================================================
 *  (C) Copyright 2007-present Facebook. All rights reserved.
 * ==========================================================
 */

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jute.compiler;

/**
 */
public class JField {
    private String mName;
    private JType  mType;

    /**
     * Creates a new instance of JField.
     */
    public JField( JType type, String name ) {
        mType = type;
        mName = name;
    }

    public String genCDecl() {
        return mType.genCDecl( mName );
    }

    public String genCppDecl() {
        return mType.genCppDecl( mName );
    }

    public String genCppGetSet( int fIdx ) {
        return mType.genCppGetSet( mName, fIdx );
    }

    public String genCsharpCompareTo() {
        return mType.genCsharpCompareTo( getCsharpName() );
    }

    public String genCsharpConstructorParam( String fname ) {
        return mType.genCsharpConstructorParam( fname );
    }

    public String genCsharpConstructorSet( String fname ) {
        return mType.genCsharpConstructorSet( mName, fname );
    }

    public String genCsharpDecl() {
        return mType.genCsharpDecl( mName );
    }

    public String genCsharpEquals() {
        return mType.genCsharpEquals( getCsharpName(),
            "peer." + getCsharpName() );
    }

    public String genCsharpGetSet( int fIdx ) {
        return mType.genCsharpGetSet( getCsharpName(), fIdx );
    }

    public String genCsharpHashCode() {
        return mType.genCsharpHashCode( getCsharpName() );
    }

    public String genCsharpReadMethodName() {
        return mType.genCsharpReadMethod( getCsharpName(), getTag() );
    }

    public String genCsharpWriteMethodName() {
        return mType.genCsharpWriteMethod( getCsharpName(), getTag() );
    }

    public String genGoCompareTo() {
        return mType.genGoCompareTo( getName() );
    }

    public String genGoConstructorParam( String fname ) {
        return mType.genJavaConstructorParam( fname );
    }

    public String genGoConstructorSet( String fname ) {
        return mType.genGoConstructorSet( mName, fname );
    }

    public String genGoDecl( JRecord container ) {
        return mType.genGoDecl( mName, container );
    }

    public String genGoEquals() {
        return mType.genGoEquals( getName(), "peer." + getName() );
    }

    public String genGoGetSet( int fIdx ) {
        return mType.genGoGetSet( mName, fIdx );
    }

    public String genGoHashCode() {
        return mType.genGoHashCode( getName() );
    }

    public String genGoReadMethodName( String prefix, JRecord container ) {
        return mType.genGoReadMethod( getName(), "", prefix, container );
    }

    public String genGoWriteMethodName( String prefix ) {
        return mType.genGoWriteMethod( getName(), "", prefix );
    }

    public String genJavaCompareTo() {
        return mType.genJavaCompareTo( getName() );
    }

    public String genJavaConstructorParam( String fname ) {
        return mType.genJavaConstructorParam( fname );
    }

    public String genJavaConstructorSet( String fname ) {
        return mType.genJavaConstructorSet( mName, fname );
    }

    public String genJavaDecl() {
        return mType.genJavaDecl( mName );
    }

    public String genJavaEquals() {
        return mType.genJavaEquals( getName(), "peer." + getName() );
    }

    public String genJavaGetSet( int fIdx ) {
        return mType.genJavaGetSet( mName, fIdx );
    }

    public String genJavaHashCode() {
        return mType.genJavaHashCode( getName() );
    }

    public String genJavaReadMethodName() {
        return mType.genJavaReadMethod( getName(), getTag() );
    }

    public String genJavaWriteMethodName() {
        return mType.genJavaWriteMethod( getName(), getTag() );
    }

    public String getCsharpName() {
        return "Id".equals( mName ) ? "ZKId" : mName;
    }

    public String getName() {
        return mName;
    }

    public String getSignature() {
        return mType.getSignature();
    }

    public String getTag() {
        return mName;
    }

    public JType getType() {
        return mType;
    }
}
