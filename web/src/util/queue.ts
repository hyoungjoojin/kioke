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

  public pop() {
    if (this.size_ === 0) {
      return;
    }

    this.front_++;
    this.size_--;
  }

  public front(): T | null {
    return this.size_ === 0 ? null : this.queue_[this.front_];
  }

  public empty(): boolean {
    return this.size_ === 0;
  }
}

export default Queue;
