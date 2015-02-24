/*
 * Copyright (C) 2015 Michael Browell <mbrowell1984@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.base.engine;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class Shader {

    private final int m_program;
    
    public Shader() {
        
        m_program = glCreateProgram();
        
        if(m_program == 0) {
            
            System.err.println("Shader creation failed: Could not find valid memory location in constructor");
            System.exit(1);
            
        }
        
    }
    
    public void bind() {
        
        glUseProgram(m_program);
        
    }
    
    public void addVertexShader(String text) {
        
        addProgram(text, GL_VERTEX_SHADER);
        
    }
    
    public void addGeometryShader(String text) {
        
        addProgram(text, GL_GEOMETRY_SHADER);
        
    }
    
    public void addFragmentShader(String text) {
        
        addProgram(text, GL_FRAGMENT_SHADER);
        
    }
    
    private void linkShader() {
        
        glLinkProgram(m_program);
        
        if(glGetShader(m_program, GL_LINK_STATUS) == 0) {
            
            System.err.println(glGetShaderInfoLog(m_program, 1024));
            System.exit(1);
            
        }
        
        glValidateProgram(m_program);
        
        if(glGetShader(m_program, GL_VALIDATE_STATUS) == 0) {
            
            System.err.println(glGetShaderInfoLog(m_program, 1024));
            System.exit(1);
            
        }
        
    }
    
    private void addProgram(String text, int type) {
        
        int shader = glCreateShader(type);
        
        if(shader == 0) {
            
            System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
            System.exit(1);
            
        }
        
        glShaderSource(shader, text);
        glCompileShader(shader);
        
        if(glGetShader(shader, GL_COMPILE_STATUS) == 0) {
            
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
            
        }
        
        glAttachShader(shader, m_program);
        
    }

}