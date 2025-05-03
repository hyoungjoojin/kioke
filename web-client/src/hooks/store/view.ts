import { useBoundStore } from '@/components/providers/StoreProvider';

export const useCurrentView = () => {
  const currentView = useBoundStore((state) => state.currentView);
  const setCurrentView = useBoundStore((state) => state.actions.setCurrentView);

  return {
    currentView,
    setCurrentView,
  };
};
