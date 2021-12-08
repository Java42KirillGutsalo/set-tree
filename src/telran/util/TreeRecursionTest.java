package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TreeRecursionTest {
TreeSet<Integer> tree;
	@BeforeEach
	void setUp() throws Exception {
		tree = new TreeSet<>();
		tree.add(30);
		tree.add(40);
		tree.add(35);
		tree.add(80);
		tree.add(60);
		tree.add(100);
		tree.add(90);
		tree.add(15);
	}

	@Test
	void widthTest() {
		assertEquals(4, tree.width());
	}
	
	@Test
	void heightTest() {
		assertEquals(5, tree.height());
	}
	
	@Test
	void displayTreeTest() {
		tree.displayTree();
		System.out.println("==============");
		getTreeForTest().displayTree();
		System.out.println("==============");
		tree.displayTreeFileSystem();
	}
	
	@Test
	void maxBranchSumTest() {
		TreeSet<Integer> tree1 = getTreeForTest();
		assertEquals(36, tree1.sumOfMaxBranch());
	}

	// V.R. OK
	private TreeSet<Integer> getTreeForTest() {
		TreeSet<Integer> treeSet = new TreeSet<>((a, b) -> getSum(a) - getSum(b));
		int array[] = {4, 6, 5, 7, 11, 21};
		for(int a: array) {
			treeSet.add(a);
		}
		return treeSet;
	}

	private int getSum(int a) {
			return a != 0 ? (a % 10) + getSum(a / 10) : 0;	
	}
}
