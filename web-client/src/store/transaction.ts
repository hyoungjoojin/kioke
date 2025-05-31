import { produce } from 'immer';
import { createStore } from 'zustand';

enum TransactionStatus {
  SAVING = 'saving',
  SAVED = 'saved',
  ERROR = 'error',
}

type TransactionState = {
  status: TransactionStatus;
};

type TransactionActions = {
  setStatus: (status: TransactionStatus) => void;
};

type TransactionStore = TransactionState & {
  actions: TransactionActions;
};

const initialState: TransactionState = {
  status: TransactionStatus.SAVED,
};

const createTransactionStore = () => {
  return createStore<TransactionStore>((set) => ({
    ...initialState,
    actions: {
      setStatus: (status: TransactionStatus) => {
        set(
          produce((store: TransactionStore) => {
            store.status = status;
          }),
        );
      },
    },
  }));
};

export { TransactionStatus, type TransactionStore, createTransactionStore };
