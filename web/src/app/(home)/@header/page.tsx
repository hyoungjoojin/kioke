'use client';

import DashboardEditorToolbar from '@/components/feature/dashboard/DashboardEditorToolbar';
import BaseHeader from '@/components/header/BaseHeader';
import { useDashboard } from '@/store/dashboard';

export default function HomeHeader() {
  const { isEditing } = useDashboard();

  return isEditing ? (
    <DashboardEditorToolbar />
  ) : (
    <BaseHeader selectedTab='home' />
  );
}
