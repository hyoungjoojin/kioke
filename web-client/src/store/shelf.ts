import { StateCreator } from "zustand";
import { produce } from "immer";
import { GetShelvesResponseBody } from "@/types/server/shelf";

interface Shelf {
  id: string;
  name: string;
  journals: {
    id: string;
    title: string;
  }[];
}

interface ShelfState {
  selectedShelfIndex: number;
  shelves: Shelf[];
}

interface ShelfActions {
  setShelves: (shelves: GetShelvesResponseBody) => void;
  getSelectedShelf: () => Shelf | null;
  setSelectedShelf: (index: number) => void;
}

export type ShelfSlice = ShelfState & {
  actions: ShelfActions;
};

const initialState: ShelfState = {
  shelves: [],
  selectedShelfIndex: -1,
};

export const createShelfSlice: StateCreator<ShelfSlice, [], [], ShelfSlice> = (
  set,
  get,
) => ({
  ...initialState,
  actions: {
    setShelves: ({ shelves }) => {
      if (shelves.length === 0) return;

      set(
        produce((state: ShelfSlice) => {
          if (state.shelves.length === 0) state.selectedShelfIndex = 0;

          state.shelves = shelves.map((shelf) => {
            return {
              id: shelf.id,
              name: shelf.name,
              journals: shelf.journals,
            };
          });
        }),
      );
    },
    getSelectedShelf: () => {
      const { selectedShelfIndex, shelves } = get();
      if (selectedShelfIndex === -1 || selectedShelfIndex >= shelves.length)
        return null;

      return shelves[selectedShelfIndex];
    },
    setSelectedShelf: (index: number) => {
      set(
        produce((state: ShelfSlice) => {
          state.selectedShelfIndex = index;
        }),
      );
    },
  },
});
