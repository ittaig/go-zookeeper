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
public class JVector extends JCompType {
    private static int level = 0;

    final JType mElement;

    private JRecord container;

    /**
     * Creates a new instance of JVector.
     */
    public JVector( JType t ) {
        super( "struct " + extractVectorName( t ),
            " ::std::vector<" + t.getCppType() + ">",
            "System.Collections.Generic.List<" + t.getCsharpType() + ">",
            "java.util.List<" + t.getJavaType() + ">",
            "[]" + t.getGoType( null ), "Vector",
            "System.Collections.Generic.List<" + t.getCsharpType() + ">",
            "java.util.ArrayList<" + t.getJavaType() + ">" );
        mElement = t;
    }

    public static String extractVectorName( JType jvType ) {
        return JRecord.extractMethodSuffix( jvType ) + "_vector";
    }

    public String genCsharpReadWrapper( String fname, String tag,
            boolean decl ) {
        StringBuilder ret = new StringBuilder( "" );

        if( decl ) {
            ret.append( "      System.Collections.Generic.List<"
                + mElement.getCsharpType() + "> " + capitalize( fname )
                + ";\n" );
        }

        ret.append( "    {\n" );
        incrLevel();
        ret.append( "      IIndex " + getId( "vidx" ) + " = a_.StartVector(\""
            + tag + "\");\n" );
        ret.append( "      if (" + getId( "vidx" ) + "!= null) {" );
        ret.append( "          " + capitalize( fname )
            + "=new System.Collections.Generic.List<" + mElement
            .getCsharpType() + ">();\n" );
        ret.append( "          for (; !" + getId( "vidx" ) + ".Done(); "
            + getId( "vidx" ) + ".Incr()) {\n" );
        ret.append( mElement.genCsharpReadWrapper( getId( "e" ), getId( "e" ),
                true ) );
        ret.append( "            " + capitalize( fname ) + ".Add("
            + getId( "e" ) + ");\n" );
        ret.append( "          }\n" );
        ret.append( "      }\n" );
        ret.append( "    a_.EndVector(\"" + tag + "\");\n" );
        decrLevel();
        ret.append( "    }\n" );

        return ret.toString();
    }

    public String genCsharpWriteWrapper( String fname, String tag ) {
        StringBuilder ret = new StringBuilder( "    {\n" );
        incrLevel();
        ret.append( "      a_.StartVector(" + capitalize( fname ) + ",\"" + tag
            + "\");\n" );
        ret.append( "      if (" + capitalize( fname ) + "!= null) {" );
        ret.append( "          int " + getId( "len" ) + " = "
            + capitalize( fname ) + ".Count;\n" );
        ret.append( "          for(int " + getId( "vidx" ) + " = 0; "
            + getId( "vidx" ) + "<" + getId( "len" ) + "; " + getId( "vidx" )
            + "++) {\n" );
        ret.append( "            " + mElement.getCsharpWrapperType() + " "
            + getId( "e" ) + " = (" + mElement.getCsharpWrapperType() + ") "
            + capitalize( fname ) + "[" + getId( "vidx" ) + "];\n" );
        ret.append( mElement.genCsharpWriteWrapper( getId( "e" ),
                getId( "e" ) ) );
        ret.append( "          }\n" );
        ret.append( "      }\n" );
        ret.append( "      a_.EndVector(" + capitalize( fname ) + ",\"" + tag
            + "\");\n" );
        ret.append( "    }\n" );
        decrLevel();

        return ret.toString();
    }

    public String genJavaCompareTo( String fname ) {
        return "    throw new UnsupportedOperationException(\"comparing "
        + fname + " is unimplemented\");\n";
    }

    public String genJavaReadMethod( String fname, String tag, String prefix ) {
        return genJavaReadWrapper( fname, tag, false );
    }

    public String genJavaReadWrapper( String fname, String tag, boolean decl ) {
        StringBuilder ret = new StringBuilder( "" );

        if( decl ) {
            ret.append( "      java.util.List " + fname + ";\n" );
        }

        ret.append( "    {\n" );
        incrLevel();
        ret.append( "      Index " + getId( "vidx" ) + " = a_.startVector(\""
            + tag + "\");\n" );
        ret.append( "      if (" + getId( "vidx" ) + "!= null) {" );
        ret.append( "          " + fname + "=new java.util.ArrayList<"
            + mElement.getJavaType() + ">();\n" );
        ret.append( "          for (; !" + getId( "vidx" ) + ".done(); "
            + getId( "vidx" ) + ".incr()) {\n" );
        ret.append( mElement.genJavaReadWrapper( getId( "e" ), getId( "e" ),
                true ) );
        ret.append( "            " + fname + ".add(" + getId( "e" ) + ");\n" );
        ret.append( "          }\n" );
        ret.append( "      }\n" );
        ret.append( "    a_.endVector(\"" + tag + "\");\n" );
        decrLevel();
        ret.append( "    }\n" );

        return ret.toString();
    }

    public String genJavaWriteMethod( String fname, String tag ) {
        return genJavaWriteWrapper( fname, tag );
    }

    public String genJavaWriteWrapper( String fname, String tag ) {
        StringBuilder ret = new StringBuilder( "    {\n" );
        incrLevel();
        ret.append( "      a_.startVector(" + fname + ",\"" + tag + "\");\n" );
        ret.append( "      if (" + fname + "!= null) {" );
        ret.append( "          int " + getId( "len" ) + " = " + fname
            + ".size();\n" );
        ret.append( "          for(int " + getId( "vidx" ) + " = 0; "
            + getId( "vidx" ) + "<" + getId( "len" ) + "; " + getId( "vidx" )
            + "++) {\n" );
        ret.append( "            " + mElement.getJavaWrapperType() + " "
            + getId( "e" ) + " = (" + mElement.getJavaWrapperType() + ") "
            + fname + ".get(" + getId( "vidx" ) + ");\n" );
        ret.append( mElement.genJavaWriteWrapper( getId( "e" ),
                getId( "e" ) ) );
        ret.append( "          }\n" );
        ret.append( "      }\n" );
        ret.append( "      a_.endVector(" + fname + ",\"" + tag + "\");\n" );
        ret.append( "    }\n" );
        decrLevel();

        return ret.toString();
    }

    public JType getElementType() {
        return mElement;
    }

    public String getSignature() {
        return "[" + mElement.getSignature() + "]";
    }

    public void setContainer( JRecord container ) {
        this.container = container;

        if( container == null ) {
            return;
        }

        final String containerPackage =
            container.getGoPackage().substring( container.getGoPackage()
                .lastIndexOf( '.' ) + 1 );

        if( mGoName.startsWith( "[]" + containerPackage + "." ) ) {
            final String n =
                mGoName.substring( mGoName.lastIndexOf( '.' ) + 1 );
            mGoName = "[]" + n;
        }
    }

    String genCsharpReadMethod( String fname, String tag ) {
        return genCsharpReadWrapper( fname, tag, false );
    }

    String genCsharpWriteMethod( String fname, String tag ) {
        return genCsharpWriteWrapper( fname, tag );
    }

    String genGoReadMethod( String fname, String tag, String prefix,
            JRecord container2 ) {
        final StringBuilder sb = new StringBuilder( "" );
        sb.append( "  var len_" + fname + " int32\n" );
        sb.append( "  len_" + fname + ", err = archive.ReadInt()\n" );
        sb.append( "  if err != nil || len_" + fname + " == -1 {\n" );
        sb.append( "    return err\n" );
        sb.append( "  }\n" );
        sb.append( "  " );
        sb.append( prefix );
        sb.append( capitalize( fname ) );
        sb.append( " = make(" );
        sb.append( getGoType( container ) );
        sb.append( ",len_" + fname + ")\n" );
        sb.append( "  for i := 0; i < int(len_" + fname + "); i++ {\n" );

        if( mElement instanceof JRecord ) {
            sb.append( ( ( JRecord )mElement ).genGoReadMethod( fname + "[i]",
                    "  ", prefix, container ) );
        } else {
            sb.append( mElement.genGoReadMethod( fname + "[i]", "  ", prefix,
                    container ) );
        }

        sb.append( "  }\n\n" );

        return sb.toString();
    }

    String genGoWriteMethod( String fname, String tag, String prefix ) {
        final StringBuilder sb = new StringBuilder( "" );
        sb.append( "  if err != nil {\n" );
        sb.append( "    return err\n" );
        sb.append( "  }\n" );
        sb.append( "  if " );
        sb.append( prefix );
        sb.append( capitalize( fname ) );
        sb.append( " == nil {\n" );
        sb.append( "    _, err = archive.WriteInt(-1)\n" );
        sb.append( "    if err != nil {\n" );
        sb.append( "      return err\n" );
        sb.append( "    }\n" );
        sb.append( "  } else {\n" );
        sb.append( "    _, err = archive.WriteInt(int32(len(" );
        sb.append( prefix );
        sb.append( capitalize( fname ) );
        sb.append( ")))\n" );
        sb.append( "    if err != nil {\n" );
        sb.append( "      return err\n" );
        sb.append( "    }\n" );
        sb.append( "    for i := 0; i < len(" );
        sb.append( prefix );
        sb.append( capitalize( fname ) );
        sb.append( ") ; i++ {\n" );
        sb.append( mElement.genGoWriteMethod( fname + "[i]", "    ", prefix ) );
        sb.append( "    }\n" );
        sb.append( "  }\n\n" );

        return sb.toString();
    }

    private static void decrLevel() {
        level--;
    }

    private static String getId( String id ) {
        return id + getLevel();
    }

    private static String getLevel() {
        return Integer.toString( level );
    }

    private static void incrLevel() {
        level++;
    }
}
