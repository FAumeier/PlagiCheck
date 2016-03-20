package actionsPackage;

/**
 * Created by Matthias on 19.03.2016.
 */
public interface IActionAtInsert {
    Object actionAtKeyFound(Object previous);
    Object actionAtKeyNotFound();
}
