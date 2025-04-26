import { type ShelfSlice, createShelfSlice } from './shelf';
import { TransactionSlice, createTransactionSlice } from './transaction';
import { ViewSlice, createViewSlice } from './view';
import { create } from 'zustand';

export type Store = ViewSlice & ShelfSlice & TransactionSlice;

const createBoundStore = () => {
  return create<Store>((...a) => {
    const viewSlice = createViewSlice(...a);
    const shelfSlice = createShelfSlice(...a);
    const transactionSlice = createTransactionSlice(...a);

    return {
      ...viewSlice,
      ...shelfSlice,
      ...transactionSlice,

      actions: {
        ...viewSlice.actions,
        ...shelfSlice.actions,
        ...transactionSlice.actions,
      },
    };
  });
};

export default createBoundStore;
