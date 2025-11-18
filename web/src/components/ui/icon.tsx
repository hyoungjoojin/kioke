import {
  ArrowUp,
  ChevronDown,
  ChevronLeft,
  ChevronRight,
  ChevronUp,
  Ellipsis,
  Image,
  ListFilter,
  type LucideProps,
  Map,
  MapPin,
  MapPinPlusInside,
  Menu,
  Plus,
  Share,
  Trash,
  Upload,
} from 'lucide-react';
import type { ForwardRefExoticComponent, RefAttributes } from 'react';

type IconName =
  | 'menu'
  | 'plus'
  | 'filter'
  | 'chevron-left'
  | 'chevron-right'
  | 'chevron-up'
  | 'chevron-down'
  | 'arrow-up'
  | 'image'
  | 'map'
  | 'ellipsis'
  | 'upload'
  | 'share'
  | 'location'
  | 'location-plus'
  | 'trash';

type IconProps = LucideProps & {
  name: IconName;
};

function Icon({ name, ...props }: IconProps) {
  const Component = map[name];
  return <Component {...props} />;
}

const map: Record<
  IconName,
  ForwardRefExoticComponent<
    Omit<LucideProps, 'ref'> & RefAttributes<SVGSVGElement>
  >
> = {
  menu: Menu,
  plus: Plus,
  filter: ListFilter,
  'chevron-left': ChevronLeft,
  'chevron-right': ChevronRight,
  'chevron-up': ChevronUp,
  'chevron-down': ChevronDown,
  'arrow-up': ArrowUp,
  image: Image,
  map: Map,
  ellipsis: Ellipsis,
  upload: Upload,
  share: Share,
  location: MapPin,
  'location-plus': MapPinPlusInside,
  trash: Trash,
};

export default Icon;
export type { IconName };
