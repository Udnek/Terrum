package me.udnekjupiter.graphic.engine.opengl;

import me.udnekjupiter.util.Initializable;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.*;

public class AttributeManager implements Initializable {

    protected ShaderProgram shaderProgram;
    protected List<Attribute> attributes = new ArrayList<>();

    public AttributeManager(@NotNull ShaderProgram shaderProgram){
        this.shaderProgram = shaderProgram;
    }

    @Override
    public void initialize(){
        addAttribute("position", 3);
        addAttribute("color", 3);
        addAttribute("texcoord", 2);


        int stride = 0;
        for (Attribute attribute : attributes) {
            stride += attribute.getSize() * Float.BYTES;
        }
        int pointer = 0;
        for (Attribute attribute : attributes) {
            String name = attribute.getName();
            int size = attribute.getSize();
            int id = glGetAttribLocation(shaderProgram.getProgram(), name);
            glEnableVertexAttribArray(id);
            glVertexAttribPointer(id, size, GL_FLOAT, false, stride, pointer);
            System.out.println(
                    MessageFormat.format("ATTRIBUTE: id={0} name={1}, size={2}, stride={3}, pointer={4}",
                            id, name, size, stride, pointer));
            pointer += size * Float.BYTES;
        }

        System.out.println("ATTRIBUTES INITIALIZED");
    }

    protected void addAttribute(@NotNull String name, int size){
        attributes.add(new Attribute(name, size));
    }

}
