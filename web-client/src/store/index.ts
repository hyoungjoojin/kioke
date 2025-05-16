import { type ModalSlice, createModalSlice } from './modal';
import { type ShelfSlice, createShelfSlice } from './shelf';
import { TransactionSlice, createTransactionSlice } from './transaction';
import { ViewSlice, createViewSlice } from './view';
import { create } from 'zustand';

export type Store = ModalSlice & ViewSlice & ShelfSlice & TransactionSlice;

const createBoundStore = () => {
  return create<Store>((...a) => {
    const modalSlice = createModalSlice(...a);
    const viewSlice = createViewSlice(...a);
    const shelfSlice = createShelfSlice(...a);
    const transactionSlice = createTransactionSlice(...a);

    return {
      ...modalSlice,
      ...viewSlice,
      ...shelfSlice,
      ...transactionSlice,

      actions: {
        ...modalSlice.actions,
        ...viewSlice.actions,
        ...shelfSlice.actions,
        ...transactionSlice.actions,
      },
    };
  });
};

export default createBoundStore;
