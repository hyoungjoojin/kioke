import { useBoundStore } from "@/components/providers/StoreProvider";
import { Shelf } from "@/types/shelf";

export const useGetSelectedShelf = (shelves: Shelf[] | undefined) => {
  const selectedShelf = useBoundStore((state) =>
    state.actions.getSelectedShelf(shelves),
  );

  return selectedShelf;
};

export const useSetSelectedShelfIndex = () => {
  const setSelectedShelfIndex = useBoundStore(
    (state) => state.actions.setSelectedShelfIndex,
  );

  return setSelectedShelfIndex;
};
