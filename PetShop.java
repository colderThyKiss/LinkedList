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

interface IPet {  // 定义宠物标准
	public String getName() ;
	public int getAge() ;
	public String getColor() ;
}

class Shop {
	private Link pets = new Link() ;  // 开辟一个链表，保存多个宠物
	public void add(IPet pet) {  // 不关心是哪个宠物（子类），只关心“宠物”这个概念
		this.pets.add(pet) ;  // 上架就是向链表中保存数据
	}
	public void delete(IPet pet) {
		this.pets.remove(pet) ;  // 下架就是从链表中删除数据
	}
	public Link getPets(IPet pet) {  // 取得全部宠物
		return this.pets ;  // 上架就是向链表中保存数据
	}
	public Link search(String keyWord) {  // 关键字查找
		Link results = new Link() ;  // 查询到的结果一定是多个宠物，所以返回值是Link类
		Object [] data = this.pets.toArray() ;
		for (int x = 0 ; x < data.length ; x++) {
			IPet pet = (IPet)data[x] ;  // 只有转型成Pet类才可以调用接口中的三个属性
			if (pet.getName().contains(keyWord) || pet.getColor().contains(keyWord)) {
				results.add(pet) ;
			}
		}
		return results ;
	}
}

class DogImpl implements IPet {
	private String name ;
	private int age ;
	private String color ;
	public DogImpl(String name, int age, String color) {  // 构造
		this.name = name ;
		this.age = age ;
		this.color = color ;
	}
	public String getName() {  // 接口方法覆写
		return this.name ;
	}
	public int getAge() {  // 接口方法覆写
		return this.age ;
	}
	public String getColor() {  // 接口方法覆写
		return this.color ;
	}
	public boolean equals(Object obj) {  // 覆写equals类
		if (obj == null) {
			return false ;
		}
		if (this == obj) {
			return true ;
		}
		if (!(obj instanceof DogImpl)) {
			return false ;
		}
		DogImpl dog = (DogImpl)obj ;
		return this.name.equals(dog.name) && this.age == dog.age && this.color.equals(dog.color) ;
	}
	public String toString() {
		return "【狗】名字：" + this.name + "，年龄：" + this.age + "，毛色：" + this.color ;
	}
}

class CatImpl implements IPet {
	private String name ;
	private int age ;
	private String color ;
	public CatImpl(String name, int age, String color) {  // 构造
		this.name = name ;
		this.age = age ;
		this.color = color ;
	}
	public String getName() {  // 接口方法覆写
		return this.name ;
	}
	public int getAge() {  // 接口方法覆写
		return this.age ;
	}
	public String getColor() {  // 接口方法覆写
		return this.color ;
	}
	public boolean equals(Object obj) {  // 覆写equals类
		if (obj == null) {
			return false ;
		}
		if (this == obj) {
			return true ;
		}
		if (!(obj instanceof CatImpl)) {
			return false ;
		}
		CatImpl cat = (CatImpl)obj ;
		return this.name.equals(cat.name) && this.age == cat.age && this.color.equals(cat.color) ;
	}
	public String toString() {
		return "【猫】名字：" + this.name + "，年龄：" + this.age + "，毛色：" + this.color ;
	}
}

public class PetShop {
	public static void main(String args[]) throws Exception {
		Shop petshop = new Shop() ;
		petshop.add(new DogImpl("卡斯罗", 2, "黑色")) ;
		petshop.add(new DogImpl("金毛", 4, "黄色")) ;
		petshop.add(new DogImpl("阿拉斯加", 3, "黑白")) ;
		petshop.add(new DogImpl("柴犬", 3, "黄白")) ;
		petshop.add(new CatImpl("暹罗猫", 1, "小黑脸")) ;
		petshop.add(new CatImpl("斯芬克斯猫", 1, "无毛")) ;
		petshop.add(new CatImpl("加菲猫", 1, "黄色")) ;
		
		Link all = petshop.search("黑") ;
		Object[] result = all.toArray() ;
		for (int i = 0 ; i < result.length ; i++) {
			System.out.println(result[i]) ;
		}
		System.out.println("==========================") ;
		
		petshop.delete(new DogImpl("卡斯罗", 2, "黑色")) ;
		Link allA = petshop.search("黑") ;
		Object[] resultA = allA.toArray() ;
		for (int i = 0 ; i < resultA.length ; i++) {
			System.out.println(resultA[i]) ;
		}
	}
}