package mypeeps;

import java.io.File;
import static java.lang.System.out;
import java.util.Date;
import static mypeeps.Utils.log;
import mypeeps.entity.Attachment;
import mypeeps.entity.Event;
import mypeeps.entity.MemoryDAO;
import mypeeps.entity.Person;
import mypeeps.entity.Place;

public class TestApp2
{

    public static void main(String[] args)
    {
        log(TestApp2.class, "main(String[])");

        File data = new File("test-memory-dao.obj");

        MemoryDAO mem1 = new MemoryDAO(data);

        if (!data.exists())
        {
            Person p1 = new Person(null, "Joe", "Blow", "male", null);
            
            p1.getChildren().add(new Person(null, "Jimmy", "Blow", "male", "yippee"));
            p1.getChildren().add(new Person(null, "Jackie", "Blow", "female", "yahoo"));
            p1.getChildren().add(new Person(null, "Jerry", "Blow", "male", "hurray"));
            p1.getChildren().forEach(child -> child.getParents().add(p1));
            
            p1.getParents().add(new Person(null, "Agnus", "Blow", "male", null));
            p1.getParents().add(new Person(null, "Abigail", "Blow", "female", null));
            p1.getParents().forEach(parent -> parent.getChildren().add(p1));
            
            p1.getEvents().add(new Event(null, "birth", new Date(), new Place(null, "Hometown", null), null));
            mem1.create(p1);

            Person p2 = new Person(null, "Jane", "Blow", "female", "la la");
            Event e1 = new Event(null, "birth", new Date(), new Place(null, "Farmville", null), null);
            e1.getAttachments().add(new Attachment(null, "farmville-townhall.png", "This is Farmville Townhall"));
            e1.getAttachments().add(new Attachment(null, "farmville-store.png", "This is the Grocery Store"));
            p2.getEvents().add(e1);
            mem1.create(p2);

            Person p3 = new Person(null, "Jed", "Blow", "male", "howdy");
            p3.getAttachments().add(new Attachment(null, "jeds-1st-bday.png", "Jed's 1st Birthday"));
            
            Person p4 = new Person(null, "Betty", "Blow", "female", null);
            p4.getParents().add(new Person(null, "Granny", "Gruesome", "female", null));
            p4.getParents().forEach(parent -> parent.getChildren().add(p4));
            
            p4.getAttachments().add(new Attachment(null, "old-granny", "Granny's 99th"));
            Event e2 = new Event(null, "99th Birthday", new Date(), new Place(null, "Big City", null), null);
            e2.getAttachments().add(new Attachment(null, "granny-99.png", "Granny's Big Day"));
            p4.getEvents().add(e2);
            p3.getParents().add(p4);
            mem1.create(p3);
        }

        App2 app = new App2(mem1);
        app.start();
    }
}
