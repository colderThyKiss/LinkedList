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
	}
	/* --------------下面定义真正的Link类----------------- */
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
	}
}