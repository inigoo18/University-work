package concurrencia;

public abstract class Lock {
	public abstract void takeLock(int name); // no hay dos usuarios con el mismo nombre
	public abstract void releaseLock(int name);
}
