import { produce } from 'immer';
import { createStore } from 'zustand';

type ShelfState = {
  selectedShelfId: string;
};

type ShelfActions = {
  setSelectedShelfId: (shelfId: string) => void;
};

type ShelfStore = ShelfState & {
  actions: ShelfActions;
};

const initialState: ShelfState = {
  selectedShelfId: '',
};

const createShelfStore = () => {
  return createStore<ShelfStore>((set) => ({
    ...initialState,
    actions: {
      setSelectedShelfId: (shelfId: string) => {
        set(
          produce((store: ShelfStore) => {
            store.selectedShelfId = shelfId;
          }),
        );
      },
    },
  }));
};

export { type ShelfStore, createShelfStore };
