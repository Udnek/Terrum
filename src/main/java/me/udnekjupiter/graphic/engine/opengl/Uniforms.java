package me.udnekjupiter.graphic.engine.opengl;

import me.udnekjupiter.util.Initializable;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Uniforms implements Initializable {

    public static String MODEL = "model";
    public static String VIEW = "view";
    public static String PROJECTION = "projection";
    public static String TEX_IMAGE = "texImage";

    protected ShaderProgram shaderProgram;
    protected Map<String, Integer> uniforms = new HashMap<>();

    public Uniforms(@NotNull ShaderProgram shaderProgram){
        this.shaderProgram = shaderProgram;
    }

    @Override
    public void initialize(){
        create(MODEL, new Matrix4f());
        create(VIEW, new Matrix4f());
        create(PROJECTION, new Matrix4f().perspective(Math.toRadians(90), 1, 0f, 1000f));
        create(TEX_IMAGE, 0);

        System.out.println("UNIFORMS INITIALIZED");
    }

    protected void create(@NotNull String name, int value){
        int id = glGetUniformLocation(shaderProgram.getProgram(), name);
        glUniform1i(id, value);
    }
    protected void create(@NotNull String name, @NotNull Matrix4f model){
        int id = glGetUniformLocation(shaderProgram.getProgram(), name);
        glUniformMatrix4fv(id, false, model.get(new float[4*4]));
        uniforms.put(name, id);
    }

    public int getId(@NotNull String name){return uniforms.get(name);}

    public void set(int id, @NotNull Matrix4f matrix){
        glUniformMatrix4fv(id, false, matrix.get(new float[4*4]));
    }

}
