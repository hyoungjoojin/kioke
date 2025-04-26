import { produce } from 'immer';
import { StateCreator } from 'zustand';

interface ShelfState {
  selectedShelfId: string;
}

interface ShelfActions {
  setSelectedShelfId: (shelfId: string) => void;
}

export type ShelfSlice = ShelfState & {
  actions: ShelfActions;
};

const initialState: ShelfState = {
  selectedShelfId: '',
};

export const createShelfSlice: StateCreator<ShelfSlice, [], [], ShelfSlice> = (
  set,
  _,
) => ({
  ...initialState,
  actions: {
    setSelectedShelfId: (shelfId: string) => {
      set(
        produce((state: ShelfSlice) => {
          state.selectedShelfId = shelfId;
        }),
      );
    },
  },
});
