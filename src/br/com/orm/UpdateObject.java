package br.com.orm;

public class UpdateObject {

	private WriteObject writeObject;
	private RemoveObject removeObject;
	
	public UpdateObject(WriteObject writeObject, RemoveObject removeObject) {
		this.writeObject = writeObject;
		this.removeObject = removeObject;
	}

	public void update(Object old, Object newObj) {
		removeObject.remove(old);
		writeObject.write(newObj);
    }
}
