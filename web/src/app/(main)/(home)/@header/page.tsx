'use client';

import HomeHeaderEditMode from './components/HomeHeaderEditMode';
import HomeHeaderNormalMode from './components/HomeHeaderNormalMode';
import { useDashboard } from '@/store/dashboard';

export default function HomeHeader() {
  const { isEditing } = useDashboard();

  return (
    <div className='flex justify-between'>
      {isEditing ? <HomeHeaderEditMode /> : <HomeHeaderNormalMode />}
    </div>
  );
}
