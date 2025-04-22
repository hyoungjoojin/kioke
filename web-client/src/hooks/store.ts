import { useBoundStore } from "@/components/providers/StoreProvider";

export const useSelectedShelfId = () => {
  const { selectedShelfId, actions } = useBoundStore((state) => state);

  return {
    selectedShelfId,
    setSelectedShelfId: actions.setSelectedShelfId,
  };
};

export const useTransactionStatus = () => {
  const { status, actions } = useBoundStore((state) => state);

  return {
    status,
    setStatus: actions.setStatus,
  };
};

export const useCurrentView = () => {
  const { currentView, actions } = useBoundStore((state) => state);

  return {
    currentView,
    setCurrentView: actions.setCurrentView,
  };
};
