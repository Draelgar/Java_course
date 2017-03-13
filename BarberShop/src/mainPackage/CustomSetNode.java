package mainPackage;

public class CustomSetNode<T extends Comparable<T>> {
	private CustomSetNode<T> mChild;
	private T mData;
	
	public CustomSetNode(T data) {
		mData = data;
		mChild = null;
	}
	
	public T getData() {
		return mData;
	}
	
	public void setData(T data) {
		mData = data;
	}
	
	public CustomSetNode<T> getChild() {
		return mChild;
	}
	
	public void setNode(CustomSetNode<T> child) {
		mChild = child;
	}
	
	public void removeChild() {
		mChild = null;
	}
	
}
