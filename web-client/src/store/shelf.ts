import { StateCreator } from "zustand";
import { produce } from "immer";
import { Shelf } from "@/types/primitives/shelf";

interface ShelfState {
  selectedShelfIndex: number;
}

interface ShelfActions {
  getSelectedShelf: (shelves: Shelf[] | undefined) => Shelf | null;
  setSelectedShelfIndex: (shelves: Shelf[] | undefined, index: number) => void;
}

export type ShelfSlice = ShelfState & {
  actions: ShelfActions;
};

const initialState: ShelfState = {
  selectedShelfIndex: -1,
};

export const createShelfSlice: StateCreator<ShelfSlice, [], [], ShelfSlice> = (
  set,
  get,
) => ({
  ...initialState,
  actions: {
    getSelectedShelf: (shelves: Shelf[] | undefined) => {
      if (shelves === undefined) {
        set(
          produce((state: ShelfSlice) => {
            state.selectedShelfIndex = -1;
          }),
        );

        return null;
      }

      const { selectedShelfIndex } = get();
      if (selectedShelfIndex >= 0 && selectedShelfIndex < shelves.length) {
        return shelves[selectedShelfIndex];
      }

      if (shelves.length > 0) {
        set(
          produce((state: ShelfSlice) => {
            state.selectedShelfIndex = 0;
          }),
        );

        return shelves[0];
      }

      set(
        produce((state: ShelfSlice) => {
          state.selectedShelfIndex = -1;
        }),
      );

      return null;
    },

    setSelectedShelfIndex: (shelves: Shelf[] | undefined, index: number) => {
      if (shelves === undefined) {
        return;
      }

      set(
        produce((state: ShelfSlice) => {
          state.selectedShelfIndex =
            index >= 0 && index < shelves.length ? index : -1;
        }),
      );
    },
  },
});
