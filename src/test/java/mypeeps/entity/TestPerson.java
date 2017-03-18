package mypeeps.entity;

import static java.lang.System.out;
import mypeeps.Utils;

public class TestPerson
{
    public static void main(String[] args)
    {
        Utils.DEBUG = false;
        
        long id = 1;
        
        Person p0 = new Person(id++, "0", "0", "", "");
        Person p1 = new Person(id++, "0", "1", "", "");
        
        Person p2 = new Person(id++, "1", "2", "", "");
        Person p3 = new Person(id++, "1", "3", "", "");
        Person p4 = new Person(id++, "1", "4", "", "");
        Person p5 = new Person(id++, "1", "5", "", "");
        relate(p0, p2, p3, p4, p5);
        relate(p1, p2, p3, p4, p5);
        
        Person p6 = new Person(id++, "0", "6", "", "");
        Person p7 = new Person(id++, "0", "7", "", "");
        
        Person p8 = new Person(id++, "1", "8", "", "");
        Person p9 = new Person(id++, "1", "9", "", "");
        Person p10 = new Person(id++, "1", "10", "", "");
        Person p11 = new Person(id++, "1", "11", "", "");
        relate(p6, p8, p9, p10, p11);
        relate(p7, p8, p9, p10, p11);
        
        Person p12 = new Person(id++, "2", "12", "", "");
        Person p13 = new Person(id++, "2", "13", "", "");
        Person p14 = new Person(id++, "2", "14", "", "");
        Person p15 = new Person(id++, "2", "15", "", "");
        relate(p2, p12, p13, p14, p15);
        relate(p8, p12, p13, p14, p15);
        
        Person p16 = new Person(id++, "2", "16", "", "");
        Person p17 = new Person(id++, "2", "17", "", "");
        Person p18 = new Person(id++, "2", "18", "", "");
        Person p19 = new Person(id++, "2", "19", "", "");
        relate(p3, p16, p17, p18, p19);
        relate(p9, p16, p17, p18, p19);
        
        Person p20 = new Person(id++, "3", "20", "", "");
        Person p21 = new Person(id++, "3", "21", "", "");
        Person p22 = new Person(id++, "3", "22", "", "");
        Person p23 = new Person(id++, "3", "23", "", "");
        relate(p12, p20, p21, p22, p23);
        relate(p16, p20, p21, p22, p23);
        
        Person p24 = new Person(id++, "3", "24", "", "");
        Person p25 = new Person(id++, "3", "25", "", "");
        Person p26 = new Person(id++, "3", "26", "", "");
        Person p27 = new Person(id++, "3", "27", "", "");
        relate(p13, p24, p25, p26, p27);
        relate(p17, p24, p25, p26, p27);
        
        Person p28 = new Person(id++, "4", "28", "", "");
        Person p29 = new Person(id++, "4", "29", "", "");
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
