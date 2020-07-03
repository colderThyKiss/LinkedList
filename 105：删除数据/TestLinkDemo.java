class Link {  // 负责链表的操作
	// 定义为私有内部类，表示Node类只为Link类服务
	private class Node {  // Node类负责保存数据、设置节点
		private Object data ;  // 要保存的数据
		private Node next ;  // 定义下一个节点
		public Node(Object data) {  // Object类：保存任意类的对象
			this.data = data ;
		}
		// 第一次调用addNode：this = Link.root
		// 第二次调用addNode：this = Link.root.next
		// 第三次调用addNode：this = Link.root.next.next
		// ······
		public void addNode (Node newNode) {  // 处理节点关系
			if (this.next == null) {  // 当前节点下一个节点为空时。可以保存
				this.next = newNode ;
			} else {  // 当前节点的下一个节点不为空时
				this.next.addNode(newNode) ;  // 下一个节点再调用addNode，直到找到下一个节点为空
			}
		}
		// 第一次调用toArrayNode：this = Link.root
		// 第二次调用toArrayNode：this = Link.root.next
		// 第三次调用toArrayNode：this = Link.root.next.next
		// ······
		public void toArrayNode () {  // 处理节点关系
			Link.this.reData[Link.this.foot++] = this.data ;
			if (this.next != null) {  // 现在还有下一个节点
				this.next.toArrayNode() ;
			}
		}
		// 第一次调用containsNode：this = Link.root
		// 第二次调用containsNode：this = Link.root.next
		// 第三次调用containsNode：this = Link.root.next.next
		// ······
		public boolean containsNode(Object search) {
			if (search.equals(this.data)) {  // 找到了想查询的数据
				return true ;
			} else {
				if (this.next != null) {  // 当前节点之后还有其他节点
					return this.next.containsNode(search) ;
				} else {  // 当前节点是最后一个节点
					return false ;
				}
			}
		}
		public Object getNode(int index) {
			if (Link.this.foot++ == index) {
				return this.data ;
			} else {
				this.next.getNode(index) ;
			}
			return null ;
		}
		public void setNode(int index, Object newData) {
			if (Link.this.foot++ == index) {
				this.data = newData;
			} else {
				if (this.next != null) {
					this.next.setNode(index,newData) ;
				}
			}
		}
		// 第一次调用removeNode：this = Link.root.next，previous = Link.root
		// 第二次调用removeNode：this = Link.root.next.next，previous = Link.root.next
		// ······
		public void removeNode(Node previous, Object data) {
			if (this.data.equals(data)) {  // 当前节点为要删除节点
				previous.next = this.next;  // 上一个节点指向当前节点
			} else {
				this.next.removeNode(this, data) ;
			}
		}
	}
	/* --------------下面定义真正的Link类----------------- */
	private Object[] reData ;  // 返回类型
	private int foot = 0 ;  // 操作脚标
	private int count = 0 ;  // 当前的保存个数
	private Node root ;  // 没有根节点就无法进行数据的保存
	public void add(Object data) {
		if (data == null) {
			return ;
		}
		// 如果想要进行数据的保存，那么必须将数据封装在Node节点类中
		// 因为如果没有封装好就无法确认节点的先后顺序
		Node newNode = new Node(data) ;
		if (this.root == null) {  // 如果当前没有根节点
			this.root = newNode ;  // 把第一个节点设置为根节点
		} else {  // 如果根节点已经存在
			// 把此时的节点顺序的处理交给Node类自己完成
			this.root.addNode(newNode) ;
		}
		this.count++ ;
	}
	public int size() {  // 取得元素个数
		return this.count ;
	}
	public boolean isEmpty() {
		return this.root == null && this.count == 0 ;
	}
	public Object[] toArray() {
		if (this.root == null && this.count == 0) {
			return null ;
		} else {
			// 链表中有数据，则开辟相应长度的数组
			// 该数组一定要交给Node类进行处理
			this.reData = new Object[this.count] ;
			this.foot = 0 ;
			this.root.toArrayNode() ;
			return this.reData ;
		}
	}
	public boolean contains(Object search) {
		if (search == null || this.root == null) {  // 没有要查询的内容并且链表为空
			return false ;
		}
		return this.root.containsNode(search) ;
	}
	public Object get(int index) {
		if (index >= this.count) {  //  超过了保存的个数
			return null ;
		}
		this.foot = 0 ;
		return this.root.getNode(index) ;
	}
	public void set(int index, Object newData) {
		if (index >= this.count) {  // 超过了保存的个数
			return ;  // void没有返回值
		}
		this.foot = 0 ;
		this.root.setNode(index, newData) ;
	}
	public void remove(Object data) {
		if (this.contains(data)) {  // 如果该数据存在则向下进行
			// 首先判断要删除的是否是根节点数据
			if (this.root.data.equals(data)) {  // root是Node类的对象，Node是Link的内部类，所以可以直接使用
				this.root = this.root.next ;  // 根节点变为下一个节点，代表原根节点被删除
			} else {
				this.root.next.removeNode(this.root, data) ;
			}
			this.count-- ;
		}
	}
}

public class TestLinkDemo {
	public static void main(String args[]) throws Exception {
		Link all = new Link() ;
		System.out.println(all.size() + " = " + all.isEmpty());
		all.add("hello") ;
		all.add("world") ;
		all.add("java") ;
		System.out.println(all.size() + " = " + all.isEmpty()) ;
		System.out.println("==========================") ;
		Object[] result = all.toArray() ;
		for (int i = 0 ; i < result.length ; i++) {
			System.out.println(result[i]) ;
		}
		System.out.println("==========================") ;
		System.out.println(all.contains("hello")) ;
		System.out.println(all.contains("theworld")) ;
		System.out.println("==========================") ;
		System.out.println(all.get(0)) ;
		System.out.println(all.get(3)) ;
		System.out.println("==========================") ;
		all.set(0, "HELLO") ;
		System.out.println(all.get(0)) ;
		System.out.println("==========================") ;
		all.remove("HELLO") ;
		all.remove("java") ;
		Object[] result1 = all.toArray() ;
		for (int i = 0 ; i < result1.length ; i++) {
			System.out.println(result1[i]) ;
		}
	}
}
