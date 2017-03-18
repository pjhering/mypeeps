package mypeeps.entity;

import static java.lang.System.out;
import mypeeps.Utils;

public class TestPerson
{
    public static void main(String[] args)
    {
        Utils.DEBUG = false;
        
        long id = 1;
        
        Person p0 = new Person(id++, "Gen 0", "Foo 0", "", "");
        Person p1 = new Person(id++, "Gen 0", "Foo 1", "", "");
        
        Person p2 = new Person(id++, "Gen 1", "Foo 2", "", "");
        Person p3 = new Person(id++, "Gen 1", "Foo 3", "", "");
        Person p4 = new Person(id++, "Gen 1", "Foo 4", "", "");
        Person p5 = new Person(id++, "Gen 1", "Foo 5", "", "");
        relate(p0, p2, p3, p4, p5);
        relate(p1, p2, p3, p4, p5);
        
        Person p6 = new Person(id++, "Gen 0", "Foo 6", "", "");
        Person p7 = new Person(id++, "Gen 0", "Foo 7", "", "");
        
        Person p8 = new Person(id++, "Gen 1", "Foo 8", "", "");
        Person p9 = new Person(id++, "Gen 1", "Foo 9", "", "");
        Person p10 = new Person(id++, "Gen 1", "Foo", "", "");
        Person p11 = new Person(id++, "Gen 1", "Foo", "", "");
        relate(p6, p8, p9, p10, p11);
        relate(p7, p8, p9, p10, p11);
        
        Person p12 = new Person(id++, "Gen 2", "Foo", "", "");
        Person p13 = new Person(id++, "Gen 2", "Foo", "", "");
        Person p14 = new Person(id++, "Gen 2", "Foo", "", "");
        Person p15 = new Person(id++, "Gen 2", "Foo", "", "");
        relate(p2, p12, p13, p14, p15);
        relate(p8, p12, p13, p14, p15);
        
        Person p16 = new Person(id++, "Gen 2", "Foo", "", "");
        Person p17 = new Person(id++, "Gen 2", "Foo", "", "");
        Person p18 = new Person(id++, "Gen 2", "Foo", "", "");
        Person p19 = new Person(id++, "Gen 2", "Foo", "", "");
        relate(p3, p16, p17, p18, p19);
        relate(p9, p16, p17, p18, p19);
        
        Person p20 = new Person(id++, "Gen 3", "Foo", "", "");
        Person p21 = new Person(id++, "Gen 3", "Foo", "", "");
        Person p22 = new Person(id++, "Gen 3", "Foo", "", "");
        Person p23 = new Person(id++, "Gen 3", "Foo", "", "");
        relate(p12, p20, p21, p22, p23);
        relate(p16, p20, p21, p22, p23);
        
        Person p24 = new Person(id++, "Gen 3", "Foo", "", "");
        Person p25 = new Person(id++, "Gen 3", "Foo", "", "");
        Person p26 = new Person(id++, "Gen 3", "Foo", "", "");
        Person p27 = new Person(id++, "Gen 3", "Foo", "", "");
        relate(p13, p24, p25, p26, p27);
        relate(p17, p24, p25, p26, p27);
        
        Person p28 = new Person(id++, "Gen 4", "Foo", "", "");
        Person p29 = new Person(id++, "Gen 4", "Foo", "", "");
        relate(p20, p28, p29);
        relate(p24, p28, p29);
        
        out.println(p0.isAncestorOf(p28));
        out.println(p28.isDescendantOf(p0));
        out.println("-----");
        printAncestors(0, p28);
        out.println("-----");
        printDescendants(0, p0);
        out.println("-----");
    }
    
    private static void printAncestors(int depth, Person p)
    {
        p.getParents().forEach(a ->
        {
            for(int i = 0; i < depth; i++)
            {
                out.print(' ');
            }
            out.println(a);
            printAncestors(depth + 1, a);
        });
    }
    
    private static void printDescendants(int depth, Person p)
    {
        p.getChildren().forEach(a ->
        {
            for(int i = 0; i < depth; i++)
            {
                out.print(' ');
            }
            out.println(a);
            printDescendants(depth + 1, a);
        });
    }
    
    private static void relate(Person parent, Person ... children)
    {
        for(Person child : children)
        {
            parent.getChildren().add(child);
            child.getParents().add(parent);
        }
    }
}
