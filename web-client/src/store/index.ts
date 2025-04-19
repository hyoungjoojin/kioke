import { create } from "zustand";
import { createShelfSlice, type ShelfSlice } from "./shelf";
import { createTransactionSlice, TransactionSlice } from "./transaction";

export type Store = ShelfSlice & TransactionSlice;

const createBoundStore = () => {
  return create<Store>((...a) => {
    const shelfSlice = createShelfSlice(...a);
    const transactionSlice = createTransactionSlice(...a);

    return {
      ...shelfSlice,
      ...transactionSlice,

      actions: {
        ...shelfSlice.actions,
        ...transactionSlice.actions,
      },
    };
  });
};

export default createBoundStore;
