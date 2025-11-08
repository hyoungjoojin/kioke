class Queue<T> {
  private queue_: T[];

  private front_: number;
  private size_: number;

  constructor() {
    this.queue_ = [];
    this.front_ = 0;
    this.size_ = 0;
  }

  public push(item: T) {
    this.queue_.push(item);
    this.size_++;
  }

  public pushAll(items: T[]) {
    this.queue_.push(...items);
    this.size_ += items.length;
  }

  public pop() {
    if (this.size_ === 0) {
      return;
    }

    this.front_++;
    this.size_--;
  }

  public flush(): T[] {
    const result = this.queue_.splice(0);

    this.queue_ = [];
    this.front_ = 0;
    this.size_ = 0;

    return result;
  }

  public front(): T | null {
    return this.size_ === 0 ? null : this.queue_[this.front_];
  }

  public empty(): boolean {
    return this.size_ === 0;
  }
}

export default Queue;
