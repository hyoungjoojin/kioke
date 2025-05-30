import { type ModalSlice, createModalSlice } from './modal';
import { type PreferencesSlice, createPreferencesSlice } from './preferences';
import { type ShelfSlice, createShelfSlice } from './shelf';
import { TransactionSlice, createTransactionSlice } from './transaction';
import { ViewSlice, createViewSlice } from './view';
import { create } from 'zustand';

export type Store = PreferencesSlice &
  ModalSlice &
  ViewSlice &
  ShelfSlice &
  TransactionSlice;

const createBoundStore = () => {
  return create<Store>((...a) => {
    const preferencesSlice = createPreferencesSlice(...a);
    const modalSlice = createModalSlice(...a);
    const viewSlice = createViewSlice(...a);
    const shelfSlice = createShelfSlice(...a);
    const transactionSlice = createTransactionSlice(...a);

    return {
      ...preferencesSlice,
      ...modalSlice,
      ...viewSlice,
      ...shelfSlice,
      ...transactionSlice,

      actions: {
        ...preferencesSlice.actions,
        ...modalSlice.actions,
        ...viewSlice.actions,
        ...shelfSlice.actions,
        ...transactionSlice.actions,
      },
    };
  });
};

export default createBoundStore;
