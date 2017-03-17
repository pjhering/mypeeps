package mypeeps.ui2;

import java.awt.Dialog;
import java.awt.Frame;
import static java.util.Objects.requireNonNull;
import mypeeps.entity.Person;

/**
 * Used to create or edit a Person
 * 
 * @author tinman
 */
public class PersonDialog
{
    
    private final Person PERSON;
    
    public PersonDialog(Person person)
    {
        this.PERSON = requireNonNull(person);
    }
    
    public Person open(Frame parent)
    {
        return PERSON;
    }
    
    public Person open(Dialog parent)
    {
        return PERSON;
    }
    
    private boolean isValid()
    {
        return false;
    }
    
    private void updatePerson()
    {
    }
    
    private void doSaveAction()
    {
    }
    
    private void doCancelAction()
    {
    }
    
    private void close()
    {
    }
}
