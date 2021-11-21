package telran.util;

import java.util.Comparator;
import java.util.Iterator;

public class TreeSet<T> extends AbstractSet<T> {
	private static class Node<T> {
		T obj;
		Node<T> left; //reference to all nodes containing objects less than obj
		Node<T> right; //reference to all nodes containing objects greater than obj
		Node<T> parent; //reference to a parent
		Node(T obj) {
			this.obj = obj;
		}
	}
	
	private Node<T> root;
	private Comparator<T> comp;

	public TreeSet(Comparator<T> comp) {
		this.comp = comp;
	}
	
	@SuppressWarnings("unchecked")
	public TreeSet() {
		this((Comparator<T>)Comparator.naturalOrder());
	}
	
	private Node<T> getMostLeftFrom(Node<T> from) {
		while(from.left != null) {
			from = from.left;
		}
		return from;
	}
	
	private Node<T> getFirstParentGreater(Node<T> node) {
		while(node.parent != null && node.parent.left != node) {
			node = node.parent;
		}
		return node.parent;
	}
	
	private class TreeSetIterator implements Iterator<T> {
	
		Node<T> current = root == null ? root : getMostLeftFrom(root);
		@Override
		public boolean hasNext() {
			
			return current != null;
		}

		

		@Override
		public T next() {
			T res = current.obj;
			current = current.right != null ? getMostLeftFrom(current.right) :
				getFirstParentGreater(current);
			return res;
		}

		
		
	}
	
	@Override
	public boolean add(T obj) {
		if (root == null) {
			addRoot(obj);
			size++;
			return true;
		}
		Node<T> parent = getParent(obj);
		//if obj already exists getParent will return null
		if (parent == null) {
			return false;
		}
		Node<T> node = new Node<>(obj);
		if(comp.compare(obj, parent.obj) < 0) {
			parent.left = node;
		} else {
			parent.right = node;
		}
		node.parent = parent;
		size++;
		return true;
	}
	
	private Node<T> getParent(T obj) {
		Node<T> current = root;
		Node<T> parent = null;
		while(current != null) {
			int res = comp.compare(obj, current.obj);
			if(res == 0) {
				return null;
			}
			parent = current;
			current = res < 0 ? current.left : current.right;
		}
		return parent;
	}

	private void addRoot(T obj) {
		root = new Node<>(obj);
	}

	@Override
	public T remove(T pattern) {
		Node<T> removedNode = getNode(pattern);
		if(removedNode == null) {
			return null;
		}
		removeNode(removedNode);
		return removedNode.obj;
	}

	private void removeNode(Node<T> removedNode) {
		if(removedNode.left != null && removedNode.right != null) {
			removeJunctionNode(removedNode);
		} else {
			removeNonJunctionNode(removedNode);
		}
		size--;
	}

	private void removeJunctionNode(Node<T> removedNode) {
		Node<T> substitutionNode = getMostLeftFrom(removedNode.right);
		removedNode.obj = substitutionNode.obj;
		if(removedNode.right == substitutionNode) {
			removedNode.right = substitutionNode.right;
			if(removedNode.right != null) {
				removedNode.right.parent = removedNode;
			}
		}
		Node<T> parent = substitutionNode.parent;
		if(parent != null && parent.right == substitutionNode) {
			parent.right = null;
		}
		substitutionNode.parent = null;
	}

	private void removeNonJunctionNode(Node<T> removedNode) {
		if(removedNode == root) {
			removedNode.parent =null;
			root = removedNode.right == null ? removedNode.left :
				removedNode.right;
		} else {
			Node<T> parent = removedNode.parent;
			Node<T> child = removedNode.right == null ? removedNode.left :
				removedNode.right;
			if(parent.right == removedNode) {
				parent.right = child;
			} else {
				parent.left = child;
			}
			if(child != null) {
				child.parent = parent;
			}
		}
		
	}

	private void removeRoot() {
		//TODO update the method by applying another algorithm
		Node<T> child = root.right == null ? root.left : root.right;
		if(child != null) {
			child.parent = null;
		}
		if(root.right != null) {
			Node<T> parentLeft = getMostLeftFrom(root.right);
			parentLeft.left = root.left;
			if(root.left != null) {
				root.left.parent = parentLeft;
			}
		}
		root = child;
	}

	private Node<T> getNode(T pattern) {
		Node<T> current = root;
		while(current != null && !current.obj.equals(pattern)) {
			current = comp.compare(pattern, current.obj) > 0 ?
					current.right : current.left;
		}
		return current;
	}

	@Override
	public Iterator<T> iterator() {
		return new TreeSetIterator();
	}
	
	@Override
	public boolean contains(T pattern) {
		return getParent(pattern) == null;
	}
}
