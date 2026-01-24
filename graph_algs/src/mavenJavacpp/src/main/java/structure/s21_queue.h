#ifndef S21_CONTAINERS_S21_QUEUE_H
#define S21_CONTAINERS_S21_QUEUE_H

#include <iostream>

namespace s21 {
template <typename T>
class queue {
 public:
  // ***************** Member type definition *****************

  using value_type = T;   // the template parameter T;
  using reference = T &;  // T & defines the type of the reference to an element
  using const_reference =
      const T &;  // const T & defines the type of the constant reference
  using size_type = size_t;  // size_t defines the type of the container size
                             // (standard type is size_t)

  // ************************* Methods ************************

  queue() : front_(nullptr), back_(nullptr), size_(0) {};

  // destructor
  ~queue() {
    while (!empty()) pop();
  }

  // ****************** Queue access methods ******************

  // access the first element
  const_reference front() {
    if (empty()) throw std::out_of_range("Queue is empty");
    return front_->data;
  }

  // access the last element
  const_reference back() {
    if (empty()) throw std::out_of_range("Queue is empty");
    return back_->data;
  }

  // checks whether the container is empty
  bool empty() { return (front_ == nullptr && back_ == nullptr && size_ == 0); }

  // returns the number of elements
  size_type size() { return size_; }

  // *********************** Modifiers ************************
  // inserts element at the end
  void push(const_reference value) {
    QueueNode *newQueueNode = new QueueNode(value);
    if (empty()) {
      front_ = newQueueNode;
    } else {
      back_->next = newQueueNode;
    }
    back_ = newQueueNode;
    ++size_;
  }

  // removes the first element
  value_type pop() {
    if (empty()) throw std::out_of_range("Queue is empty");
    QueueNode *tmp = front_;
    value_type result = tmp->data;
    front_ = front_->next;
    delete tmp;
    --size_;
    if (front_ == nullptr) {
      back_ = nullptr;
    }
    return result;
  }
  // ************************* Fields *************************
 private:
  // ***************** Queue node declaration *****************
  struct QueueNode {
    value_type data;
    QueueNode *next;
    QueueNode(const_reference value) : data(value), next(nullptr) {}
  };

  QueueNode *front_;
  QueueNode *back_;
  size_type size_;
};
};  // namespace s21

#endif  // S21_CONTAINERS_S21_QUEUE_H
