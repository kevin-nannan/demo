package com.example.netty_study;

import java.util.*;

/**
 * @author zck
 * @Description
 * @createTime 2022-04-23 21:10
 * @other
 */
public class EnumDemo {
    public static void main(String[] args) {
        EnumDemo enumDemo = new EnumDemo();
        enumDemo.switchDemo();
        enumDemo.enumsetDemo();
        enumDemo.enumMapDemo();
    }

    private void switchDemo() {
        for (Animal animal : Animal.values()) {
            switch (animal) {
                case Dog:
                    System.out.println("switch result is dog:" + animal.toString());
                    break;
                case CAT:
                    System.out.println("switch result is cat:" + animal.toString());
                    break;
            }
        }

    }

    private  void enumsetDemo(){
        EnumSet<Animal> enumset = EnumSet.allOf(Animal.class);
        System.out.println(enumset);
        System.out.println("------------------------");
        EnumSet<Animal> enumSet2 = EnumSet.noneOf(Animal.class);
        enumSet2.add(Animal.OTHER);
        System.out.println(enumSet2);
        System.out.println("------------------------");

        EnumSet<Animal> enumSet3 = EnumSet.complementOf(enumSet2);
        System.out.println(enumSet3);

    }

    private void enumMapDemo() {
        EnumMap<Animal, String> enumMap = new EnumMap<>(Animal.class);
        for (Animal entity : Animal.values()) {
            enumMap.put(entity, entity.getName());
        }
        System.out.println(enumMap);//直接打印集合
        System.out.println("--------------------------");
        System.out.println("分别取得Key/Value：");
        Set<Map.Entry<Animal, String>> set = enumMap.entrySet();
        Iterator<Map.Entry<Animal, String>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Animal, String> me = iterator.next();
            //getKey就是toString();
            System.out.println(me.getKey() + "=" + me.getValue());//以迭代器循环的方式分别取得键值对
        }
    }
}

enum Animal {
    CAT("cat", "喵喵喵"),
    Dog("dog", "汪汪汪"),
    OTHER("other", "汪汪汪");
    private String name;
    private String call;

    Animal(String name, String call) {
        this.name = name;
        this.call = call;
    }

    public String getCall() {
        return this.call;
    }

    public String getName() {
        return this.name;
    }

//    @Override
//    public String toString() {
//        return "Animal{" +
//                "name='" + name + '\'' +
//                ", call='" + call + '\'' +
//                '}';
//    }
}
