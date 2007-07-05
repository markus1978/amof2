package hub.sam.util;

public class OrderedTuple<T1, T2> {
	private final T1 o1;
	private final T2 o2;
	
	public OrderedTuple(final T1 o1, final T2 o2) {
		super();
		this.o1 = o1;
		this.o2 = o2;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((o1 == null) ? 0 : o1.hashCode());
		result = PRIME * result + ((o2 == null) ? 0 : o2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OrderedTuple other = (OrderedTuple) obj;
		if (o1 == null) {
			if (other.o1 != null)
				return false;
		} else if (!o1.equals(other.o1))
			return false;
		if (o2 == null) {
			if (other.o2 != null)
				return false;
		} else if (!o2.equals(other.o2))
			return false;
		return true;
	}

	public T1 getO1() {
		return o1;
	}

	public T2 getO2() {
		return o2;
	}	
}
