import { produce } from 'immer';
import { StateCreator } from 'zustand';

export enum TransactionStatus {
  SAVING = 'saving',
  SAVED = 'saved',
  ERROR = 'error',
}

interface TransactionState {
  status: TransactionStatus;
}

interface TransactionActions {
  setStatus: (status: TransactionStatus) => void;
}

export type TransactionSlice = TransactionState & {
  actions: TransactionActions;
};

const initialState: TransactionState = {
  status: TransactionStatus.SAVED,
};

export const createTransactionSlice: StateCreator<
  TransactionSlice,
  [],
  [],
  TransactionSlice
> = (set, _) => ({
  ...initialState,
  actions: {
    setStatus: (status: TransactionStatus) => {
      set(
        produce((state: TransactionSlice) => {
          state.status = status;
        }),
      );
    },
  },
});
