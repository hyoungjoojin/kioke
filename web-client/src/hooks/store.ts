import { useBoundStore } from "@/components/providers/StoreProvider";

export const useShelf = () => {
  const shelves = useBoundStore((state) => state.shelves);
  const selectedShelf = useBoundStore((state) =>
    state.actions.getSelectedShelf(),
  );

  return { shelves, selectedShelf };
};

export const useShelfActions = () => {
  const { setShelves, getSelectedShelf, setSelectedShelf } = useBoundStore(
    (state) => state.actions,
  );

  return { setShelves, getSelectedShelf, setSelectedShelf };
};
