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
	}
	
	@Test
	void maxBranchSumTest() {
		TreeSet<Integer> tree1 = getTreeForTest();
		assertEquals(36, tree1.sumOfMaxBranch());
	}

	private TreeSet<Integer> getTreeForTest() {
		Integer [] numbers = {4, 6, 11, 21, 7, 5};
		TreeSet<Integer> tree = new TreeSet<>((a, b) -> getDigitsSum(a) - getDigitsSum(b));
		for(int num: numbers) {
			tree.add(num);
		}
		tree.displayTree();
		System.out.println("**********************");
		return tree;
	}

	private int getDigitsSum(Integer a) {
		int res = 0;
		do {
			res += a % 10;
			a /= 10;
		}while(a != 0); 
		return res;
	}
}
