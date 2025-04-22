import { create } from "zustand";
import { createShelfSlice, type ShelfSlice } from "./shelf";
import { createTransactionSlice, TransactionSlice } from "./transaction";
import { createViewSlice, ViewSlice } from "./view";

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
